function execute(go, constants)
	-- switch door state
	go:setUse(not go:isUsed());

	-- set new params
	go:setPassable(go:isUsed());
	go:setLos(not go:isUsed());

	-- set new texture
	if(go:isUsed() == true) then
		go:setTexture(const_1);
	else 
		go:setTexture(const_2);
	end
end