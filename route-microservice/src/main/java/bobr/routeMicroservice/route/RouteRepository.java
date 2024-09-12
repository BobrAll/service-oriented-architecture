package bobr.routeMicroservice.route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends PagingAndSortingRepository<Route, Integer>, JpaRepository<Route, Integer>, JpaSpecificationExecutor<Route> {

    List<Route> findAllByDistanceLessThan(double distance);

    List<Route> findAllByDistanceGreaterThan(double distance);

    @Query("SELECT DISTINCT r.distance from Route r")
    List<Double> findDistinctDistances();

}
