import com.fasterxml.jackson.annotation.JsonProperty
import com.lee.net.httpclient.safeJackson
import org.junit.jupiter.api.Test
import com.lee.openai.api.chat.ToolChoice
import org.junit.jupiter.api.Assertions.assertNull
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ToolChoiceTest{

    private val objectMapper = safeJackson
    @Test
    fun testSerializeStringMode() {
        val request = Request(ToolChoice.Auto)
        val json = objectMapper.writeValueAsString(request)
        assertEquals("""{"tool_choice":"auto"}""", json)
    }

    @Test
    fun testSerializeNamed() {
        val request = Request(ToolChoice.function("my_function"))
        val json = objectMapper.writeValueAsString(request)
        assertEquals("""{"tool_choice":{"type":"function","function":{"name":"my_function"}}}""", json)
    }

    @Test
    fun testDeserializeStringMode() {
        val json = """{"tool_choice":"auto"}"""
        val request = objectMapper.readValue(json,Request::class.java)
        assertTrue(request.toolChoice is ToolChoice.StringMode)
        assertEquals("auto", (request.toolChoice as ToolChoice.StringMode).value)
    }

    @Test
    fun testDeserializeNamed() {
        val json = """{"tool_choice":{"type":"function","function":{"name":"my_function"}}}"""
        val request = objectMapper.readValue(json,Request::class.java)
        assertTrue(request.toolChoice is ToolChoice.Named)
        val named = request.toolChoice as ToolChoice.Named
        assertEquals("function", named.type)
        assertEquals("my_function", named.function.name)
    }

    @Test
    fun testDeserializeNull() {
        val json = """{}"""
        val request = objectMapper.readValue(json,Request::class.java)
        assertNull(request.toolChoice)
    }

}

data class Request(
    @JsonProperty("tool_choice")
    val toolChoice: ToolChoice? = null
)