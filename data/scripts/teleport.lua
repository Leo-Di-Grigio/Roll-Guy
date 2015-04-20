function execute(type, user, target)
	if(type == EVENT_SCRIPT_GO_USE) then
		if(user ~= nil and target ~= nil and target:isGO()) then
			local teleport = location:getGO(target:getGUID());
			location:teleport(user, teleport);
		end
	end
end