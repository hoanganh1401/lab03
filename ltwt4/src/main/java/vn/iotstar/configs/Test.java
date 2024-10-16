package vn.iotstar.configs;
import jakarta.persistence.*;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Video;

public class Test {
	public static void main(String[] args) {
		EntityManager enma = JpaConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
		    trans.begin();
		    trans.commit();
		} catch (Exception e) {
		    e.printStackTrace();
		    trans.rollback();
		    throw e;
		} finally {
		    enma.close();
		}

	}
}
