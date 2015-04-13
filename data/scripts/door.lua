function execute(arr)
	if(arr[3] ~= nil) then
		local go = arr[3];

		-- switch door state
		go:setUse(not go:isUsed());

		-- set new params
		go:setPassable(go:isUsed());
		go:setLos(not go:isUsed());

		-- set new texture
		if(go:isUsed() == true) then
			go:setTexture(TEX_GO_DOOR_OPEN);
		else
			go:setTexture(TEX_GO_DOOR_CLOSE);
		end
	end
end