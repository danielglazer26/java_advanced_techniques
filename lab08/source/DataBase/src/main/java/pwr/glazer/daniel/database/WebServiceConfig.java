package pwr.glazer.daniel.database;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/finance/*");
    }

    @Bean(name = "event")
    public DefaultWsdl11Definition defaultWsdl11DefinitionEvent(XsdSchema eventSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("EventPort");
        wsdl11Definition.setLocationUri("/finance");

        wsdl11Definition.setSchema(eventSchema);

        return wsdl11Definition;
    }

    @Bean(name = "person")
    public DefaultWsdl11Definition defaultWsdl11DefinitionPerson(XsdSchema personSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PersonPort");
        wsdl11Definition.setLocationUri("/finance");
        wsdl11Definition.setSchema(personSchema);

        return wsdl11Definition;
    }

    @Bean(name = "payment")
    public DefaultWsdl11Definition defaultWsdl11DefinitionPayment(XsdSchema paymentSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PaymentPort");
        wsdl11Definition.setLocationUri("/finance");
        wsdl11Definition.setSchema(paymentSchema);

        return wsdl11Definition;
    }

    @Bean(name = "repayment")
    public DefaultWsdl11Definition defaultWsdl11DefinitionRepayment(XsdSchema repaymentSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("RepaymentPort");
        wsdl11Definition.setLocationUri("/finance");
        wsdl11Definition.setSchema(repaymentSchema);

        return wsdl11Definition;
    }

    @Bean(name = "all")
    public DefaultWsdl11Definition defaultWsdl11DefinitionAll(XsdSchema allSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AllPort");
        wsdl11Definition.setLocationUri("/finance");
        wsdl11Definition.setSchema(allSchema);

        return wsdl11Definition;
    }

    @Bean
    public XsdSchema eventSchema() {
        return new SimpleXsdSchema(new ClassPathResource("event.xsd"));
    }

    @Bean
    public XsdSchema personSchema() {
        return new SimpleXsdSchema(new ClassPathResource("person.xsd"));
    }

    @Bean
    public XsdSchema paymentSchema() {
        return new SimpleXsdSchema(new ClassPathResource("payment.xsd"));
    }

    @Bean
    public XsdSchema repaymentSchema() {
        return new SimpleXsdSchema(new ClassPathResource("repayment.xsd"));
    }

    @Bean
    public XsdSchema allSchema() {
        return new SimpleXsdSchema(new ClassPathResource("financeManagement.xsd"));
    }
}