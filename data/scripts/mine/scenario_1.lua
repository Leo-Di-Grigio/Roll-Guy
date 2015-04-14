-- Плитка срабатывает при условии, что игрок наступит на неё
-- После этого справнится наш Археолог и идёт к артефакту

function execute(arr)
	if(arr[3]:isUsed() == false and arr[1] == CONTEXT_LAND) then
		if(arr[2] ~= nil and arr[2]:isPlayer() == true) then
			--
			blockWP();

			-- realtime on
			location:realTime();
			
			-- spawn
			local npc = location:spawnNPC(3, 118, 97);
			local torch = location:spawnGO(6, 118, 97);

			if(npc ~= nil) then
				if(torch ~= nil) then
					-- grab spawned torch
					location:drag(npc, torch);
				end

				location:addWP(npc, 81, 0, 100);
			end
		end
	end
end

function blockWP()
	-- block another WP
	local go1 = location:getGO(68);
	local go2 = location:getGO(69);
	local go3 = location:getGO(70);
	local go4 = location:getGO(71);
			
	if(go1 ~= nil and go2 ~= nil and go3 ~= nil and go4 ~= nil) then
		go1:setUse(true);
		go2:setUse(true);
		go3:setUse(true);
		go4:setUse(true);
	end	
end