function execute(type, user, target)
	if(target:isGO() and type == EVENT_SCRIPT_LAND) then
		local testGO = location:getGO(target:getGUID());
	
		if(not testGO:isUsed()) then
			if(user ~= nil and user:isPlayer()) then
				testGO:setUse(true);

				local npc = location:spawnNPC(1, 6, 4);

				if(npc ~= nil) then
					location:addWP(npc, 15, 0, 20);
					location:addWP(npc, 11, 1, 20);
					location:addWP(npc, 12, 2, 20);
					location:addWP(npc, 13, 3, 20);
					location:addWP(npc, 14, 4, 20);
				end
			end
		end
	end
end