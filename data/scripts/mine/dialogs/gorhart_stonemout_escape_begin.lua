function execute(dialog, player, npc)
	if(global:getInt("stage") == 1) then
		ui:dialogEnd();
		console:splash("Горхат: Насто время размазать ваши черепа по стенам шахты!");
		ai:addEnemy(location:getNPC(118), location:getNPC(122));
	end
end