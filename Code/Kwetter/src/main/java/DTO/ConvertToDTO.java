/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Yannick van Leeuwen
 * @param <DTO> DTO that gets converted
 */
public interface ConvertToDTO<DTO> {
    DTO convert(DTOBase dtoBase);
}
