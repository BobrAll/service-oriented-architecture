package bobr.routeMicroservice.route;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class RouteSpecification {

    public static Specification<Route> hasId(Integer id) {
        return (root, query, criteriaBuilder) ->
                id != null ? criteriaBuilder.equal(root.get("id"), id) : null;
    }

    public static Specification<Route> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name != null ? criteriaBuilder.like(root.get("name"), "%" + name + "%") : null;
    }

    public static Specification<Route> hasCoordinates(Long x, Long y) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (x != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("coordinates").get("x"), x));
            }
            if (y != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("coordinates").get("y"), y));
            }
            return predicate;
        };
    }

    public static Specification<Route> hasLocationFrom(Double x, Integer y, Float z) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (x != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("from").get("x"), x));
            }
            if (y != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("from").get("y"), y));
            }
            if (z != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("from").get("z"), z));
            }
            return predicate;
        };
    }

    public static Specification<Route> hasLocationTo(Double x, Integer y, Float z) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (x != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("to").get("x"), x));
            }
            if (y != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("to").get("y"), y));
            }
            if (z != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("to").get("z"), z));
            }
            return predicate;
        };
    }

}
