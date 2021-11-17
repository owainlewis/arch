/*
 * Copyright © 2020 Owain Lewis <owain@owainlewis.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.owainlewis.arch;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An outcome is a computation result that either succeeds or fails
 *
 * It is identical to an `Either` type
 *
 * @param <L> A failure case
 * @param <R> A success case
 */
final class Outcome<L, R> {

  private final Optional<L> failure;

  private final Optional<R> success;

  private Outcome(Optional<L> l, Optional<R> r) {
    failure = l;
    success = r;
  }

  public static <L, R> Outcome<L, R> failure(L value) {
    return new Outcome<>(Optional.of(value), Optional.empty());
  }

  public static <L, R> Outcome<L, R> success(R value) {
    return new Outcome<>(Optional.empty(), Optional.of(value));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  public <T> T map(Function<? super L, ? extends T> lFunc, Function<? super R, ? extends T> rFunc) {
    return failure.<T>map(lFunc).orElseGet(() -> success.map(rFunc).get());
  }

  /**
   * Returns true if a computation is successful
   * @return boolean
   */
  public boolean isSuccess() {
    return this.success.isPresent();
  }

  /**
   * Returns true if a computation has failed
   * @return boolean
   */
  public boolean isFailure() {
    return this.failure.isPresent();
  }

  public <T> Outcome<T, R> mapLeft(Function<? super L, ? extends T> lFunc) {
    return new Outcome<>(failure.map(lFunc), success);
  }

  public <T> Outcome<L, T> mapRight(Function<? super R, ? extends T> rFunc) {
    return new Outcome<>(failure, success.map(rFunc));
  }

  public void apply(Consumer<? super L> lFunc, Consumer<? super R> rFunc) {
    failure.ifPresent(lFunc);
    success.ifPresent(rFunc);
  }
}
