function execute(arr)
	if(arr[3]:isUsed() == false and arr[1] == CONTEXT_LAND) then
		if(arr[2] ~= nil and arr[2]:isPlayer() == true) then
			arr[3]:setUse(true);

			local npc = location:spawnNPC(1, 6, 4);

			if(npc ~= nil) then
				local guid = npc:getGUID();

				location:addWP(guid, 15, 0, 20);
				location:addWP(guid, 11, 1, 20);
				location:addWP(guid, 12, 2, 20);
				location:addWP(guid, 13, 3, 20);
				location:addWP(guid, 14, 4, 20);

				location:spawnNPC(2, 6, 6);
				location:kill(6, 6);
			end
		end
	end
end