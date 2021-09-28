import {createSlice} from "@reduxjs/toolkit";

const userSlice = createSlice({
    name: 'user',
    initialState: {
        username: '',
        name: '',
        authorities: [],
        id: null,
        jwt: ''
    },
    reducers: {
        logIn(state, action) {
            action = action.payload
            state.username = action.username;
            state.name = action.name;
            state.authorities = action.authorities;
            state.id = action.id;
            state.jwt = action.jwt;
        },
        logOut(state, action) {
            state.username = '';
            state.name = '';
            state.authorities = [];
            state.id = null;
            state.jwt = '';
        }
    }
});

export const userActions = userSlice.actions;

export default userSlice;