package acorn.calendar.config.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.annotation.MapMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class CustRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter{

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		if (getArgumentResolvers() != null) {
			List<HandlerMethodArgumentResolver> resolvers = new ArrayList<HandlerMethodArgumentResolver>(getArgumentResolvers());
			resolvers.add(new AcornMapArgumentResolver());

			int mapMethodProcessorInx = -1;
			int commandMapInx = -1;
			HandlerMethodArgumentResolver commandMapArgResolver = null;

			for (int inx = 0; inx < resolvers.size(); inx++) {
				HandlerMethodArgumentResolver resolver = resolvers.get(inx);
				if (resolver instanceof MapMethodProcessor) {
					mapMethodProcessorInx = inx;
				} else if (resolver instanceof AcornMapArgumentResolver) {
					commandMapInx = inx;
				}
			}

			if (commandMapInx != -1) {
				commandMapArgResolver = resolvers.remove(commandMapInx);
				resolvers.add(mapMethodProcessorInx, commandMapArgResolver);
				setArgumentResolvers(resolvers);
			}
		}
	}
	
}
