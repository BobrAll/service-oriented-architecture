package bobr.routeMicroservice.mappers;

import bobr.routeMicroservice.route.Route;
import https.help_me.please.SoapRoute;
import https.help_me.please.UpdateRouteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RouteMapper {

    public abstract Route map(SoapRoute route);

    public abstract Route map(UpdateRouteRequest route);

    public abstract SoapRoute map(Route route);

}