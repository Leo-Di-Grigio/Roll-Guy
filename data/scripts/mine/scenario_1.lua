-- Плитка срабатывает при условии, что игрок наступит на неё
-- После этого справнится наш Археолог и идёт к артефакту

function execute(type, user, target)
	if(type == EVENT_SCRIPT_LAND) then
		local go = location:getGO(target:getGUID());
		
		if(not go:isUsed()) then
			if(user ~= nil and user:isPlayer()) then
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
end

function blockWP()
	-- block another WP
	for guid = 68, 71 do
		local go = location:getGO(guid)
		
		if(go ~= nil) then
			go:setUse(value);
		end
	end
end