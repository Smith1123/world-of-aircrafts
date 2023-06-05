package hu.epam.mentoring.l3.zsf;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitalizer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        var context =
            new AnnotationConfigWebApplicationContext();
        context.scan(
            "hu.epam.mentoring.l3.zsf.controller", "hu.epam.mentoring.l3.zsf.engine"
        );
        container.addListener(new ContextLoaderListener(context));

        var dispatcher =
                container.addServlet("mvc", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}