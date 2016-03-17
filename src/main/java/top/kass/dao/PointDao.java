package top.kass.dao;

import top.kass.model.Point;

import java.util.List;

public interface PointDao {

    public Point create(int id, int point, int type, Integer consumptionId);

    public List<Point> findByCustomerId(int id);

}
