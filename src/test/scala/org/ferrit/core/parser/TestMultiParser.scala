package org.ferrit.core.parser

import org.ferrit.core.http.Response
import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers


class TestMultiParser extends FlatSpec with ShouldMatchers {
  
  behavior of "MultiParser"

  it should "handle HTML media type and not CSS" in {
    
    def responseOf(contentType: String):Response = {
      val r = mock(classOf[Response])
      when (r.contentType) thenReturn Some(contentType)
      r
    }

    val parser = MultiParser.default
    parser.canParse(responseOf("text/html")) should equal (true)
    parser.canParse(responseOf("text/css")) should equal(false)
    parser.canParse(responseOf("html")) should equal (false)
    parser.canParse(responseOf("text")) should equal (false)
    parser.canParse(responseOf("")) should equal (false)

    intercept[ParseException] { parser.parse(responseOf("text/xml")) }
    intercept[ParseException] { parser.parse(responseOf("")) }

  }

}