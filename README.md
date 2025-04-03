# AI-Hub: Unified LLM Access Library

AI-Hub is a powerful, flexible Kotlin library that provides a unified interface for interacting with various Large Language Model (LLM) providers. It simplifies the management of multiple AI service providers, handles authentication, load balancing, and fallback mechanisms transparently.

## Features

### 1. Polymorphic Serialization with Jackson

- **Cross-Platform Compatibility**: Client and server can both use the library with identical data structures
- **Redis Context Management**: Built-in support for storing and retrieving conversation contexts in Redis
- **Utility Classes**: Helper classes for common operations with comprehensive test coverage

### 2. Flexible Resource Pool Configuration

- **Multiple Provider Support**: Configure multiple AI providers in one central place
- **Easy Channel Addition**: Add new providers with minimal configuration
- **Multi-Key Support**: Configure multiple API keys per provider for better rate limit management
- **Load Balancing**: Automatically distribute requests across providers and keys using round-robin, weighted random, or other strategies

### 3. Simple and Intuitive API

- **Capability-Based Selection**: Request models based on capabilities (vision, reasoning, etc.) rather than specific models
- **Model-Specific Requests**: Alternatively, request specific models when needed
- **Provider Abstraction**: Get a provider with a simple selector and make requests immediately

```kotlin
// Get a provider by capability
val provider = aiHub.getProvider(
    selector = ProviderSelector.reasoning()
)

// Get a specific model type with optional source filter
fun getSpecificNextReasoningCompletionConfig(
    modelType: String,
    sources: Set<String>? = null
): Provider {
   return aiHub.getProvider(
        selector = ProviderSelector.specificModels(modelType = modelType, sources = sources)
    )
}

// Make an OpenAI request
val request = openaiReasoningRequest(
    messages = messages,
    model = aiProvider.modelName,
    maxTokens = maxTokens,
    maxCompletionTokens = maxCompletionTokens,
    temperature = temperature,
    reasoningEffort = reasoningEffort,
    user = user,
    stream = streaming
)

return openai(aiProvider.config).chatCompletions(request)
```

### 4. Provider Health Monitoring

- **Automatic Health Checks**: Monitor provider availability and performance
- **Smart Fallbacks**: Automatically route around unhealthy providers
- **Error Tracking**: Track error rates and success counts per provider

### 5. Supported Providers

The library supports multiple AI providers including:

- OpenAI (GPT models)
- Claude (Anthropic)
- Groq
- DeepSeek
- Nvidia 
- VolcanoEngine
- SiliconFlow
- Novita
- Grok
- OpenRouter (meta-provider)

### 6. DSL Configuration

Configure your AI providers with a clean, Kotlin-style DSL:

```kotlin
val aiHub = AIHub.create {
    openai(apiKey = "sk-XXX") {
        gpt4o()
    }
    
    deepSeekBulk(listOf("apiKey1", "apiKey2")) {
        deepSeekR1()
    }
    
    groqBulk(listOf("apiKey1", "apiKey2", "apiKey3")) {
        llama3_2_90B()
    }
    
    // Configure health check behavior
    healthCheckInterval = 30.seconds
    unhealthyRecheckInterval = 2.minutes
}
```

## Getting Started

### Installation

Add the dependency to your project:

```kotlin
// build.gradle.kts
implementation("com.lee:koai:1.0.0")
```

### Basic Usage

```kotlin
// Create the AI hub with your providers
val aiHub = AIHub.create {
    openai(apiKey = System.getenv("OPENAI_API_KEY")) {
        gpt4o()
    }
    
    claude(apiKey = System.getenv("ANTHROPIC_API_KEY")) {
        claude3Opus()
    }
}

// Get a provider based on capabilities
val provider = aiHub.getProvider(
    selector = ProviderSelector(
        capabilities = setOf(ModelCapability.VISION)
    )
)

// Use the provider
val request = // create your request
val response = // make your API call
```

## Examples

### Using Provider Selectors

```kotlin
// Select a provider with reasoning capabilities
val reasoningProvider = aiHub.getProvider(ProviderSelector.reasoning())

// Select a provider with vision capabilities
val visionProvider = aiHub.getProvider(ProviderSelector.vision())

// Select a specific model type across all providers
val gpt4oProvider = aiHub.getProvider(
    ProviderSelector.specificModels(modelType = "gpt-4o", sources = null)
)

// Select a specific model from a specific source
val openAIGpt4Provider = aiHub.getProvider(
    ProviderSelector.specificModels(
        modelType = "gpt-4", 
        sources = setOf("openai")
    )
)
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details. 