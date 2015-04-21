function execute(type, user, target)
	
	if(type == EVENT_PLAYER_DEAD) then
		game:endGame();
	end
end