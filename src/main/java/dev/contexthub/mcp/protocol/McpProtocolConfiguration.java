package dev.contexthub.mcp.protocol;

import java.util.ArrayList;
import java.util.List;

import dev.contexthub.task.mcp.TaskTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for MCP Protocol.
 * This class defines the beans and configurations required for the MCP
 * Protocol.
 */
@Configuration
public class McpProtocolConfiguration {

    /**
     * Creates a ToolCallbackProvider bean that provides task tools.
     *
     * @param taskTool The TaskTool instance to be used.
     * @return A ToolCallbackProvider configured with the provided TaskTool.
     */
    @Bean
    public ToolCallbackProvider taskTools(TaskTool taskTool) {
    return MethodToolCallbackProvider.builder().toolObjects(taskTool).build();
    }

    // @Bean
    // @Lazy
    // TODO : This is not working as expected, we need to find a way to register all
    // tools in the application context
    public ToolCallbackProvider registerAllTools(ApplicationContext applicationContext) {
        List<Object> toolBeans = findAllToolBeans(applicationContext);
        System.out.print("Registering all tools using application context method getBeansWithAnnotation : " + toolBeans);
        return MethodToolCallbackProvider.builder()
                .toolObjects(toolBeans).build();
    }

    private List<Object> findAllToolBeans(ApplicationContext applicationContext) {
        List<Object> toolBeans = new ArrayList<>();
        
        // Get all bean names from the ApplicationContext
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            Class<?> beanClass = AopUtils.getTargetClass(bean); // handles proxies
            
            // Check if the class has @Tool annotation
            if (beanClass.isAnnotationPresent(Tool.class)) {
                toolBeans.add(bean);
            }
        }
        
        return toolBeans;
    }    
}