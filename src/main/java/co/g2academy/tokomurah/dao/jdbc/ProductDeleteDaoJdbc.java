package co.g2academy.tokomurah.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ifnub
 */
@Component
public class ProductDeleteDaoJdbc {
    private static final String DELETE =
            "delete from t_product";
    private static final String DELETE_BY_ID =
            "delete from t_product where id=?";
    @Autowired
    private DataSource dataSource;
    public void deleteProductById(Integer id){
        try{
            Connection c = dataSource.getConnection();
            PreparedStatement ps = c.prepareStatement(DELETE_BY_ID);
            ps.setInt(1, id);
            ps.execute();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public void deleteProducts(){
        try{
            Connection c = dataSource.getConnection();
            PreparedStatement ps = c.prepareStatement(DELETE);
            ps.execute();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
