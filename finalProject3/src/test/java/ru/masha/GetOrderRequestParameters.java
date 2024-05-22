package ru.masha;

import java.util.List;

public class GetOrderRequestParameters {
    private final Integer courierId;
    private final List<Integer> nearestStation;
    private final Integer limit;
    private final Integer page;

    public GetOrderRequestParameters(Integer courierId, List<Integer> nearestStation, Integer limit, Integer page) {
        this.courierId = courierId;
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public List<Integer> getNearestStation() {
        return nearestStation;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPage() {
        return page;
    }
}
