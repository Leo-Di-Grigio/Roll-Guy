function execute(type, user, target)
	
	-- действия при загрузке локаций
	if(type == EVENT_LOCATION_LOAD) then
		console:splash("Локация: Лагерь Картеля");

		-- инициализация глобальных флагов
		global:setFlag("player_dead_mine", false);
	end

	-- действия при смене локации
	if(type == EVENT_LOCATION_CHANGE) then
		
	end

	-- действия при смерти игрока
	if(type == EVENT_PLAYER_DEAD) then
		if(user:isPlayer()) then
			if(global:getFlag("player_dead_mine")) then
				-- вас убивают не в шахте
				game:endGame();
			else
				-- вас убивают в шахте
				console:splash("Вас сильно оглушили и в таком состоянии отнесли в лагерь");
				location:teleport(user, location:getMapId(), 383, 39);
				global:setFlag("player_dead_mine", true);
			end
		end
	end
end