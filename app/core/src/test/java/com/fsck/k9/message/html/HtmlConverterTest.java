package com.fsck.k9.message.html;


import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class HtmlConverterTest {
    @Test
    public void testTextQuoteToHtmlBlockquote() {
        String message = "Panama!\r\n" +
                "\r\n" +
                "Bob Barker <bob@aol.com> wrote:\r\n" +
                "> a canal\r\n" +
                ">\r\n" +
                "> Dorothy Jo Gideon <dorothy@aol.com> espoused:\r\n" +
                "> >A man, a plan...\r\n" +
                "> Too easy!\r\n" +
                "\r\n" +
                "Nice job :)\r\n" +
                ">> Guess!";

        String result = HtmlConverter.textToHtml(message);

        assertEquals("<pre dir=\"auto\" class=\"k9mail\">"
                + "Panama!<br>"
                + "<br>"
                + "Bob Barker &lt;bob@aol.com&gt; wrote:<br>"
                +
                "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #729fcf; padding-left: 1ex;\">"
                + " a canal<br>"
                + "<br>"
                + " Dorothy Jo Gideon &lt;dorothy@aol.com&gt; espoused:<br>"
                +
                "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #ad7fa8; padding-left: 1ex;\">"
                + "A man, a plan...<br>"
                + "</blockquote>"
                + "Too easy!<br>"
                + "</blockquote>"
                + "<br>"
                + "Nice job :)<br>"
                +
                "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #729fcf; padding-left: 1ex;\">"
                +
                "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #ad7fa8; padding-left: 1ex;\">"
                + "Guess!"
                + "</blockquote>"
                + "</blockquote>"
                + "</pre>", result);
    }

    @Test
    public void testTextQuoteToHtmlBlockquoteIndented() {
        String message = "*facepalm*\r\n" +
                "\r\n" +
                "Bob Barker <bob@aol.com> wrote:\r\n" +
                "> A wise man once said...\r\n" +
                ">\r\n" +
                ">     LOL F1RST!!!!!\r\n" +
                ">\r\n" +
                "> :)";

        String result = HtmlConverter.textToHtml(message);

        assertEquals("<pre dir=\"auto\" class=\"k9mail\">"
                + "*facepalm*<br>"
                + "<br>"
                + "Bob Barker &lt;bob@aol.com&gt; wrote:<br>"
                + "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #729fcf; padding-left: 1ex;\">"
                +   " A wise man once said...<br>"
                +   "<br>"
                +   "     LOL F1RST!!!!!<br>"
                +   "<br>"
                +   " :)"
                + "</blockquote></pre>", result);

    }

    @Test
    public void testQuoteDepthColor() {
        String message = "zero\r\n" +
                "> one\r\n" +
                ">> two\r\n" +
                ">>> three\r\n" +
                ">>>> four\r\n" +
                ">>>>> five\r\n" +
                ">>>>>> six";

        String result = HtmlConverter.textToHtml(message);

        assertEquals("<pre dir=\"auto\" class=\"k9mail\">"
                + "zero<br>"
                + "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #729fcf; padding-left: 1ex;\">"
                +   "one<br>"
                +   "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #ad7fa8; padding-left: 1ex;\">"
                +     "two<br>"
                +     "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #8ae234; padding-left: 1ex;\">"
                +       "three<br>"
                +       "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #fcaf3e; padding-left: 1ex;\">"
                +         "four<br>"
                +         "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #e9b96e; padding-left: 1ex;\">"
                +           "five<br>"
                +           "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #ccc; padding-left: 1ex;\">"
                +             "six"
                +           "</blockquote>"
                +         "</blockquote>"
                +       "</blockquote>"
                +     "</blockquote>"
                +   "</blockquote>"
                + "</blockquote>"
                + "</pre>", result);
    }

    @Test
    public void testPreserveSpacesAtFirst() {
        String message = "foo\r\n"
                + " bar\r\n"
                + "  baz\r\n";

        String result = HtmlConverter.textToHtml(message);

        assertEquals("<pre dir=\"auto\" class=\"k9mail\">"
                + "foo<br>"
                + " bar<br>"
                + "  baz<br>"
                + "</pre>", result);
    }

    @Test
    public void testPreserveSpacesAtFirstForSpecialCharacters() {
        String message =
                " \r\n"
                        + "  &\r\n"
                        + "    \n"
                        + "   <\r\n"
                        + "  > \r\n";

        String result = HtmlConverter.textToHtml(message);

        assertEquals("<pre dir=\"auto\" class=\"k9mail\">"
                + " <br>"
                + "  &amp;<br>"
                + "    <br>"
                + "   &lt;<br>"
                + "<blockquote class=\"gmail_quote\" style=\"margin: 0pt 0pt 1ex 0.8ex; border-left: 1px solid #729fcf; padding-left: 1ex;\">"
                + "<br>"
                + "</blockquote>"
                + "</pre>", result);
    }

    @Test
    public void issue2259Spec() {
        String text = "text\n" +
                "---------------------------\n" +
                "some other text\n" +
                "===========================\n" +
                "more text\n" +
                "-=-=-=-=-=-=-=-=-=-=-=-=-=-\n" +
                "scissors below\n" +
                "-- >8 --\n" +
                "other direction\n" +
                "-- 8< --\n" +
                "end";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\">text<hr>" +
                "some other text<hr>" +
                "more text<hr>" +
                "scissors below<hr>" +
                "other direction<hr>" +
                "end</pre>",
                result);
    }

    @Test
    public void dashesContainingSpacesIgnoredAsHR() {
        String text = "hello\n--- --- --- --- ---\nfoo bar";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\">hello<br>--- --- --- --- ---<br>foo bar</pre>",
                result);
    }

    @Test
    public void mergeConsecutiveBreaksIntoOne() {
        String text = "hello\n------------\n---------------\nfoo bar";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\">hello<hr>foo bar</pre>", result);
    }

    @Test
    public void dashedHorizontalRulePrefixedWithTextIgnoredAsHR() {
        String text = "hello----\n\n";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\">hello----<br><br></pre>", result);
    }

    @Test
    public void doubleMinusIgnoredAsHR() {
        String text = "--\n";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\">--<br></pre>", result);
    }

    @Test
    public void doubleEqualsIgnoredAsHR() {
        String text = "==\n";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\">==<br></pre>", result);
    }

    @Test
    public void doubleUnderscoreIgnoredAsHR() {
        String text = "__\n";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\">__<br></pre>", result);
    }

    @Test
    public void anyTripletIsHRuledOut() {
        String text = "--=\n-=-\n===\n___\n\n";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\"><hr></pre>", result);
    }

    @Test
    public void replaceSpaceSeparatedDashesWithHR() {
        String text = "hello\n---------------------------\nfoo bar";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\">hello<hr>foo bar</pre>", result);
    }

    @Test
    public void replacementWithHRAtBeginning() {
        String text = "---------------------------\nfoo bar";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\"><hr>foo bar</pre>", result);
    }

    @Test
    public void replacementWithHRAtEnd() {
        String text = "hello\n__________________________________";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\">hello<hr></pre>", result);
    }

    @Test
    public void replacementOfScissorsByHR() {
        String text = "hello\n-- %< -------------- >8 --\nworld\n";
        String result = HtmlConverter.textToHtml(text);
        assertEquals("<pre dir=\"auto\" class=\"k9mail\">hello<hr>world<br></pre>", result);
    }

    @Test
    public void htmlToText_withLineBreaks() {
        String input = "One<br>Two<br><br>Three";

        String result = HtmlConverter.htmlToText(input);

        assertEquals("One\nTwo\n\nThree", result);
    }

    @Test
    public void htmlToText_withBlockElements() {
        String input = "<p>One</p><p>Two<br>Three</p><div>Four</div>";

        String result = HtmlConverter.htmlToText(input);

        assertEquals("One\n\nTwo\nThree\n\nFour", result);
    }
}
