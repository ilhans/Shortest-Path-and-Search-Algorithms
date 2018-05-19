
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alper
 */
public interface Algorithm {

    /**
     * @param cityofOrigin start point of path
     * @param targetCity finish point of path
     * 
     */
    public void startAlgorithm(String cityofOrigin, String targetCity);
    /**
     * @param cityofOrigin start point of path
     * @param targetCity finish point of path
     * 
     */
    public String PrintDistance(String cityOfOrigin, String destination);
 /**
     * @param cityofOrigin start point of path
     * @param targetCity finish point of path
     * @return List<String> 
     */
    public List<String> PrintPath(String cityofOrigin, String targetCity);

    public String getNameOfAlgorithm();
}
