package space.hookszhang.duiker.config.swagger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile({"swagger"})
@Api(hidden = true)
public class SwaggerController {

    @Autowired
    private SwaggerGeneratorService swaggerGenerator;

    @ApiOperation(value = "/swagger/query", hidden = true)
    @RequestMapping(path = "/swagger/query", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/plain")
    public String swaggerQuery() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        return objectMapper.writeValueAsString(swaggerGenerator.getSwagger());
    }
}
