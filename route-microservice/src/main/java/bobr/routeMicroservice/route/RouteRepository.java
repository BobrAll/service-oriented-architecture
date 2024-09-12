package bobr.routeMicroservice.route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends PagingAndSortingRepository<Route, Integer>, JpaRepository<Route, Integer>, JpaSpecificationExecutor<Route> {

}
