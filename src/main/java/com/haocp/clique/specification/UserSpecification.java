package com.haocp.clique.specification;

import com.haocp.clique.entity.Like;
import com.haocp.clique.entity.Match;
import com.haocp.clique.entity.User;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserSpecification {

    public static Specification<User> userSpecification(Long currentUserId, List<Long> swipeOrderIds){
        return (root, query, cb) -> {
            assert query != null;
            var predicates = cb.conjunction();
            predicates = cb.and(predicates,
                    cb.notEqual(root.get("id"), currentUserId));

            predicates = cb.and(predicates,
                    cb.isTrue(root.get("enabled")));

            if (swipeOrderIds != null && !swipeOrderIds.isEmpty()) {
                predicates = cb.and(predicates,
                        cb.not(root.get("id")
                                .in(swipeOrderIds)));
            }

            Subquery<Long> likedSubquery = query.subquery(Long.class);
            var likeRoot = likedSubquery.from(Like.class);

            likedSubquery.select(likeRoot.get("liked").get("id"))
                    .where(
                            cb.equal(
                                    likeRoot.get("liker").get("id"),
                                    currentUserId
                            )
                    );

            predicates = cb.and(predicates,
                    cb.not(root.get("id").in(likedSubquery)));

            Subquery<Long> matchSubquery1 = query.subquery(Long.class);
            var matchRoot1 = matchSubquery1.from(Match.class);

            matchSubquery1.select(matchRoot1.get("user2").get("id"))
                    .where(
                            cb.equal(
                                    matchRoot1.get("user1").get("id"),
                                    currentUserId
                            )
                    );

            Subquery<Long> matchSubquery2 = query.subquery(Long.class);
            var matchRoot2 = matchSubquery2.from(Match.class);

            matchSubquery2.select(matchRoot2.get("user1").get("id"))
                    .where(
                            cb.equal(
                                    matchRoot2.get("user2").get("id"),
                                    currentUserId
                            )
                    );

            predicates = cb.and(predicates,
                    cb.not(root.get("id").in(matchSubquery1)));

            predicates = cb.and(predicates,
                    cb.not(root.get("id").in(matchSubquery2)));

            return predicates;
        };
    }

}
