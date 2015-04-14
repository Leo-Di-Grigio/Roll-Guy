-- Плитка срабатывает при условии, что игрок наступит на неё
-- После этого справнится наш Археолог и идёт к артефакту

function execute(arr)
	if(arr[3]:isUsed() == false and arr[1] == CONTEXT_LAND) then
		if(arr[2] ~= nil and arr[2]:isPlayer() == true) then
			arr[3]:setUse(true);
			
			--
			local npc = location:spawnNPC(3, 118, 97);

			if(npc ~= nil) then
				location:addWP(npc:getGUID(), 81, 0, 100);
			end
		end
	end
end