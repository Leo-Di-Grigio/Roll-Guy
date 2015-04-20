function execute(type, user, target)
	if(type == EVENT_SCRIPT_GO_USE) then  
		if(target ~= nil and target:isGO()) then
			local door = location:getGO(target:getGUID());

			-- switch door state
			door:setUse(not door:isUsed());

			-- set new params
			door:setPassable(door:isUsed());
			door:setLos(not door:isUsed());

			-- set new texture
			if(door:isUsed() == true) then
				door:setTexture(TEX_GO_DOOR_OPEN);
			else
				door:setTexture(TEX_GO_DOOR_CLOSE);
			end
		end
	end
end