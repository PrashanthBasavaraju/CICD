package com.triconinfotech.WealthWise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.triconinfotech.WealthWise.entity.Role;
import com.triconinfotech.WealthWise.exception.CustomException;
import com.triconinfotech.WealthWise.repository.RoleRepository;

/**
 * The Class RoleServiceImpl.
 */
@Service
public class RoleServiceImpl implements RoleServiceInterface {

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    /** The role repository. */
    @Autowired
    private RoleRepository roleRepository;
    
    private static final String ROLE_NOT_FOUND = "Role not found";

    /**
     * Merge role.
     *
     * @param role the role
     * @return the role
     */
    @Override
    @Transactional
    public Role mergeRole(Role role) {
        try {
            return roleRepository.save(role);
        } catch (Exception e) {
            logger.error("Error occurred while merging role: {}", e.getMessage());
            throw new CustomException("Role failed to merge", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the all roles.
     *
     * @param offset the offset
     * @param limit the limit
     * @return the all roles
     * @throws CustomException the custom exception
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles(long offset, long limit) throws CustomException {
        try {
            logger.info("Fetching all roles from service implementation file");
            long adjustedOffset = offset > 0 ? offset - 1 : 0;
            return roleRepository.findAllWithOffsetAndLimit(adjustedOffset, limit);
        } catch (Exception e) {
            logger.error("An error occurred while fetching all roles: {}", e.getMessage());
            throw new CustomException("Failed to fetch all roles", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the role by id.
     *
     * @param id the id
     * @return the role by id
     * @throws CustomException the custom exception
     */
    @Override
    @Transactional(readOnly = true)
    public Role getRoleById(Integer id) throws CustomException {
        logger.info("Fetching role by id: {} from service implementation file", id);
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.filter(role -> role.getDeletedAt() == null)
                           .orElseThrow(() -> {
                               logger.error("Role not found or already deleted with id: {} from service implementation file", id);
                               throw new CustomException(ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
                           });
    }

    /**
     * Delete role.
     *
     * @param id the id
     * @throws CustomException the custom exception
     */
    @Override
    @Transactional
    public void deleteRole(Integer id) throws CustomException {
        logger.info("Deleting role with id: {}", id);
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            if (role.getDeletedAt() == null) {
                // Proceed with deletion
                roleRepository.deleteById(id);
                logger.info("Role with id {} deleted successfully", id);
            } else {
                logger.error("Role with id {} is already deleted", id);
                throw new CustomException(ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } else {
            logger.error("Role not found with id: {} from service implementation file", id);
            throw new CustomException(ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
