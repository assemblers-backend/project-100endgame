package io.assemblers.project100endgame.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.assemblers.project100endgame.wallet.entity.Wallet;

public interface WallteRepository extends JpaRepository<Wallet, Long> {

	Optional<Wallet> findByUserId(Long userId);
}
