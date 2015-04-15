function execute(arr)
	-- 124-134
	-- 138-148
	if(arr[2] ~= nil and arr[2]:isPlayer() == true) then
		if(arr[1] == CONTEXT_LAND) then
			if(arr[3]:isUsed()) then
				setUsed(false);
			else
				setUsed(true);
				console:print("AI: alarm 1");
			end
		end
	end
end

function setUsed(value)
	for guid = 124, 134 do
		local go = location:getGO(guid)
		
		if(go ~= nil) then
			go:setUse(value);
		end
	end
end