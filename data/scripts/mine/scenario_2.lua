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
			console:splash("Стражник: Эй! А ну вернись обратно за работу, мразь!");
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