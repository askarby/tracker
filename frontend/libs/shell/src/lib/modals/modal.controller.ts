export abstract class ModalController<T> {
  abstract close: (response?: T) => void;
}
