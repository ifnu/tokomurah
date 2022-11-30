package co.g2academy.tokomurah.dao.jdbc;

import co.g2academy.tokomurah.model.Product;
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
public class ProductSaveDaoJdbc {
    private static String INSERT = 
            "insert into t_product(name, description, price, stock) "
            + "values(?, ?, ?, ?)";
    private static String UPDATE = 
            "update t_product set name = ?, description = ?, "
            + "price = ?, stock = ? where id = ?";
    @Autowired
    private DataSource dataSource;
    
    public void save(Product product){
        try{
            Connection c = dataSource.getConnection();
            if(product.getId() == null){
                PreparedStatement ps = c.prepareStatement(INSERT);
                setParameterToPreparedStatement(ps, product);
                ps.execute();
            } else {
                PreparedStatement ps = c.prepareStatement(UPDATE);
                setParameterToPreparedStatement(ps, product);
                ps.setInt(5, product.getId());
                ps.execute();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public void setParameterToPreparedStatement(
            PreparedStatement ps, Product p) throws SQLException{
        ps.setString(1, p.getName());
        ps.setString(2, p.getDescription());
        ps.setInt(3, p.getPrice());
        ps.setInt(4, p.getStock());
    }
    
}
