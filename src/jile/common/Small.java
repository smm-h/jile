// package jile.common;

// interface Quality<B> {
// boolean is(B q);

// static <T> boolean is(T subject, Quality<? super T> q) {
// return false;
// }
// }

// // interface CanHaveQualities {
// // boolean is(Quality<? extends CanHaveQualities> quality);
// // }

// class Person {

// int coolness;

// Person(int coolness) {
// this.coolness = coolness;
// }
// }

// class Main {
// static void main(String[] args) {

// // let's define who the "cool people" are
// Quality<Person> coolPeople = new Quality<Person>() {
// @Override
// public boolean is(Person person) {
// return person.coolness > 1000;
// }
// };

// // let's create a person with a coolness of just over a thousand
// Person coolPerson = new Person(1001);

// // do "cool people" include our "cool person"?
// System.out.println(coolPeople.is(coolPerson));

// // does our "cool person" qualify as a one of the "cool people"?
// System.out.println(Quality.is(coolPerson, coolPeople));
// }
// }