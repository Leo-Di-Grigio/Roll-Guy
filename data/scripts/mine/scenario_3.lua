function execute(type, user, target)
	-- 124-134
	-- 138-148
	if(type == EVENT_SCRIPT_LAND and user ~= nil and user:isPlayer()) then
		local go = location:getGO(target:getGUID());

		if(go:isUsed()) then
			setUsed(false);
		else
			setUsed(true);

			-- yell alarm!
			console:splash("Стражник: Сейчас ты у нас получишь жаренных гвоздей!");
			
			-- kill player order
			local player = location:getCreature(user:getGUID());
			ai:addEnemy(location:getNPC(122), player);
			ai:addEnemy(location:getNPC(123), player);
			ai:addEnemy(location:getNPC(159), player);
			ai:addEnemy(location:getNPC(160), player);
		end
	end
end

function setUsed(value)
	for guid = 138, 148 do
		local go = location:getGO(guid)
		
		if(go ~= nil) then
			go:setUse(value);
		end
	end
end