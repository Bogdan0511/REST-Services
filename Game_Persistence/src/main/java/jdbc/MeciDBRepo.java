package jdbc;

import model.Entity;
import model.Meci;
import org.springframework.stereotype.Component;
import persistence.MeciRepository;
import persistence.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MeciDBRepo implements MeciRepository {

    private final DBAccess dbAccess = new DBAccess();

    public MeciDBRepo() {
    }

    @Override
    public Meci find(Integer id) throws RepositoryException {
        String sql = "SELECT * FROM games WHERE id =?";
        Meci meci = null;
        try(Connection connection = dbAccess.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int idMeci = resultSet.getInt("id");
                String home = resultSet.getString("home");
                String away = resultSet.getString("away");
                String type = resultSet.getString("type");
                int seats = resultSet.getInt("seats");

                meci = new Meci(idMeci, home, away, type, seats);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return meci;
    }

    @Override
    public Meci find(Entity<Integer> integerEntity) throws RepositoryException {
        return null;
    }

    @Override
    public List<Meci> findAll() {
        List<Meci> meciuri = new ArrayList<>();

        try(Connection connection = dbAccess.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM games");
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                int idMeci = resultSet.getInt("id");
                String home = resultSet.getString("home");
                String away = resultSet.getString("away");
                String type = resultSet.getString("type");
                int seats = resultSet.getInt("seats");

                Meci meci = new Meci(idMeci, home, away, type, seats);
                meciuri.add(meci);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return meciuri;
    }

    @Override
    public Meci save(Meci meci) {
        String sql = "INSERT INTO games(home,away,type,seats) VALUES(?,?,?,?)";
        try (Connection connection = dbAccess.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, meci.getHome());
            preparedStatement.setString(2, meci.getAway());
            preparedStatement.setString(3, meci.getType());
            preparedStatement.setInt(4, meci.getSeats());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                meci.setId(generatedId); // Set the generated ID in the Meci object
            } else {
                System.out.println("No generated ID retrieved");
                // You can consider throwing an exception or handling the case where no generated ID is available.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meci;
    }

    @Override
    public Meci delete(Meci meci){
        String sql = "DELETE FROM games WHERE id = ?";
        try(Connection connection = dbAccess.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, meci.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Meci delete_id(int id) {
        String sql = "DELETE FROM games WHERE id = ?";
        try(Connection connection = dbAccess.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Meci update(Meci updateEntity) throws RepositoryException {
        return null;
    }

    @Override
    public Meci update(Meci meci, int myId) {
        String sql = "UPDATE games set home = ?, away = ?, type = ?, seats = ? where id = ?";

        try(Connection connection = dbAccess.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,meci.getHome());
            preparedStatement.setString(2,meci.getAway());
            preparedStatement.setString(3,meci.getType());
            preparedStatement.setInt(4,meci.getSeats());
            preparedStatement.setInt(5,myId);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return meci;
    }
}
