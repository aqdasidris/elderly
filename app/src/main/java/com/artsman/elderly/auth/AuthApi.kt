package com.artsman.elderly.auth

import com.artsman.elderly.Resources

interface IAuthApi{
    suspend fun register():Resources<String>
}


class FakeAuthAPI: IAuthApi{

    override suspend fun register(): Resources<String> {
        return Resources.success("")
    }


}