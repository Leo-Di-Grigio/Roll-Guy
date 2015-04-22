function execute(type, user, target)
	
	-- действия при загрузке локаций
	if(type == EVENT_LOCATION_LOAD) then
		console:splash("Локация: Лагерь Картеля");

		-- инициализация глобальных флагов
		global:setInt("stage", 1);
		global:setFlag("player_dead_mine", false);

		-- открытие глобальных тем диалогов
		global:unlockPermanentTopic(1); -- биография
	end

	-- действия при смене локации
	if(type == EVENT_LOCATION_CHANGE) then
		
	end

	-- действия при смерти игрока
	if(type == EVENT_PLAYER_DEAD) then
		if(global:getInt("stage") == 1) then
			-- вас убивают на этапе 1
			console:splash("Вас сильно оглушили и в таком состоянии отнесли в лагерь");
			location:teleport(user, location:getMapId(), 383, 39);
			user:setStructPercent(50);
			global:setInt("stage", 2);
		else
			-- вас убивают на других этапах
			game:endGame();
		end
	end
end