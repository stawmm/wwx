package com.xgw.wwx.mapper;

import java.util.List;

import com.xgw.wwx.dto.db.CardDTO;

public interface CardMapper {

	public CardDTO getCardById(Long id);

	public List<CardDTO> findCards(Long deviceId);

	public void createCard(CardDTO cardDTO);

	public void updateCard(CardDTO cardDTO);

	public void deleteCard(Long id);

}
