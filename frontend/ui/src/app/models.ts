export interface City {
  id: number,
  name: string,
  restaurants: Array<Restaurant>
}

export interface Restaurant {
  id: number,
  name: string,
  rating: number,
  maxCapacity: number,
  currentFreeCapacity: number,
  restaurantCategory: string,
  numberOfPhotos: number,
  cityId: number,
  reservations: Array<Reservation>

}

export interface Reservation {
  id: number,
  numberOfGuests: number,
  date: Date,
  email: string,
  restaurantName: string
  restaurantId: number
}

export interface User {
  email: string,
  role: Role,
  name: string,
  surname: string,
  phoneNumber: string,
  password: string,
  reservations: Array<Reservation>,
  accessToken: string,
  refreshToken: string
}

export enum Role {
  USER = 'USER',
  ADMIN = 'ADMIN'
}
