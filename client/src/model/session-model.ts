export type SessionModel = LoggedOut | LoggedIn;

export interface LoggedOut {
  state: 'loggedOut';
}

export interface LoggedIn {
  state: 'loggedIn';
  email: string;
  token: string;
}


