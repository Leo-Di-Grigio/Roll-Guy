function execute(arr)
	-- 124-134
	-- 138-148
	if(arr[2] ~= nil and arr[2]:isPlayer() == true) then
		if(arr[1] == CONTEXT_LAND) then
			if(arr[3]:isUsed()) then
				setUsed(false);
			else
				setUsed(true);

				-- yell alarm!
				console:splash("Стражник: Сейчас ты у нас получишь жаренных гвоздей!");

				-- kill player order
				ai:addEnemy(location:getNPC(122), arr[2]);
				ai:addEnemy(location:getNPC(123), arr[2]);
				ai:addEnemy(location:getNPC(159), arr[2]);
				ai:addEnemy(location:getNPC(160), arr[2]);
			end
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