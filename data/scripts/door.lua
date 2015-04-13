-- arr[0] == type
-- arr[1] == context
-- arr[2] == user
-- arr[3] == target

function execute(arr)
	if(arr[1] == CONTEXT_GO_USE) then  
		if(arr[3] ~= nil) then
			local door = arr[3];
		
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