package no.difi.vefa.rulesupport;

import org.oclc.purl.dsdl.schematron.AssertReportType;
import org.oclc.purl.dsdl.schematron.Name;
import org.oclc.purl.dsdl.schematron.Rule;
import org.oclc.purl.dsdl.schematron.ValueOf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Page {

    private String identifier;
    private String flag;
    private String message;
    private String context;
    private List<String> test = new ArrayList<>();
    private String content;

    public Page(Rule rule, AssertReportType assertReportType) {
        this.identifier = assertReportType.getId();
        this.flag = assertReportType.getFlag();
        this.message = fixMessage(assertReportType.getContent());
        this.context = rule.getContext();
        this.test.add(assertReportType.getTest());
    }

    private String fixMessage(List<Object> content) {
        return content.stream().map(o -> {
            if (o instanceof String)
                return (String) o;
            else if (o instanceof Name)
                return "[Name]";
            else if (o instanceof ValueOf)
                return String.format("[ValueOf: %s]", ((ValueOf) o).getSelect());
            else
                return o.toString();
        }).collect(Collectors.joining(""));
    }

    public void addAssert(AssertReportType assertReportType) {
        this.test.add(assertReportType.getTest());
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getFlag() {
        return flag;
    }

    public String getFlagNormalized() {
        return flag.equalsIgnoreCase("warning") ? "WARNING" : "ERROR";
    }

    public String getMessage() {
        return message;
    }

    public String getContext() {
        return context;
    }

    public List<String> getTest() {
        return test;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Page{" +
                "identifier='" + identifier + '\'' +
                ", flag='" + flag + '\'' +
                ", message='" + message + '\'' +
                ", context='" + context + '\'' +
                ", test=" + test +
                ", content='" + content + '\'' +
                '}';
    }
}
