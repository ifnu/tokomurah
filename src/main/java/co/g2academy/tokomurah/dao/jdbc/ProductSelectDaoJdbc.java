package co.g2academy.tokomurah.dao.jdbc;

import co.g2academy.tokomurah.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ifnub
 */
@Component
public class ProductSelectDaoJdbc {
    @Autowired
    private DataSource dataSource;
    private static String SELECT =  "select * from t_product";
    private static String SELECT_BY_ID =  
            "select * from t_product where id=?";
    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        try{
            Connection c = dataSource.getConnection();
            PreparedStatement ps = c.prepareStatement(SELECT);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                products.add(getProductFromResultSet(rs));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return products;
    }
    public Product getProductById(Integer id){
        try{
            Connection c = dataSource.getConnection();
            PreparedStatement ps = c.prepareStatement(SELECT_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return getProductFromResultSet(rs);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public Product getProductFromResultSet(ResultSet rs) throws SQLException{
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getInt("price"));
        p.setStock(rs.getInt("stock"));
        return p;
    }
    
}
