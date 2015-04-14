function execute(arr)
	if(arr[1] == CONTEXT_LAND) then
		if(arr[2] ~= nil) then
			if(arr[2]:isPlayer() == true) then
				console:print("test");

				location:addWP(18, 15, 0, 20);
				location:addWP(18, 11, 1, 20);
				location:addWP(18, 12, 2, 20);
				location:addWP(18, 13, 3, 20);
				location:addWP(18, 14, 4, 20);
			end
		end
	end
end