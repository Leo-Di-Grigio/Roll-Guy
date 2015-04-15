function execute(arr)
	if(arr[3]:isUsed() == false and arr[1] == CONTEXT_LAND) then
		if(arr[2] ~= nil and arr[2]:isPlayer() == true) then
			arr[3]:setUse(true);

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