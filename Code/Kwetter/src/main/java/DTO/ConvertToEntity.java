/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Yannick van Leeuwen
 * @param <Entity> To convert to an entity.
 */
public interface ConvertToEntity<Entity> {
    Entity convert(DTOBase dtoBase);
}
